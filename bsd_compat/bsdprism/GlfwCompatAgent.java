package bsdprism;
import java.lang.instrument.*;
import java.security.ProtectionDomain;

public class GlfwCompatAgent {
    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader l, String name, Class<?> c, ProtectionDomain pd, byte[] b) {
                if (name == null || name.startsWith("java/") || name.startsWith("sun/") || name.startsWith("jdk/") || name.startsWith("org/lwjgl/")) return null;
                byte[] needle = "mallocStack".getBytes();
                boolean changed = false;
                for (int i = 0; i <= b.length - needle.length; i++) {
                    boolean match = true;
                    for (int j = 0; j < needle.length; j++) {
                        if (b[i + j] != needle[j]) { match = false; break; }
                    }
                    if (match && i >= 3 && b[i - 3] == 1) {
                        int len = ((b[i - 2] & 0xFF) << 8) | (b[i - 1] & 0xFF);
                        if (len == 11) {
                            b[i - 1] = 6;
                            byte[] out = new byte[b.length - 5];
                            System.arraycopy(b, 0, out, 0, i);
                            System.arraycopy("malloc".getBytes(), 0, out, i, 6);
                            System.arraycopy(b, i + 11, out, i + 6, b.length - (i + 11));
                            b = out;
                            changed = true;
                        }
                    }
                }
                return changed ? b : null;
            }
        });
    }
}
