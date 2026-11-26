# BSDprism

**BSDprism** is an independent, unofficial fork of [ElyPrismLauncher / Prism Launcher](https://github.com) ported to run natively on **FreeBSD** (14.x / 15.x).

> **Disclaimer:**
> This project is an independent community port for FreeBSD. It is **not** affiliated with, endorsed by, or supported by PineconeMC, ElyPrismLauncher, Prism Launcher, Mojang Studios, or Microsoft. Please report any FreeBSD-specific issues strictly in this repository (`mellstroydmitry-maker/bsdprism`).

## Key FreeBSD Changes
- **Native LWJGL 2 & LWJGL 3 Integration:** Automatically maps Minecraft LWJGL `.jar` and native `.so` libraries to FreeBSD system packages (`/usr/local/lib/lwjgl`, `/usr/local/lib/lwjgl3`) and skips extracting incompatible Linux binaries.
- **Full Minecraft Version Support (1.7.10 – 1.21+):**
  - Includes a lightweight bytecode agent (`bsdprism-glfw-fix.jar`) that transparently patches legacy `GLFWImage.mallocStack` calls to `GLFWImage.malloc` for Minecraft 1.13 – 1.18.2.
  - Forces `GLFW_PLATFORM=x11` for Minecraft 1.20+ to prevent `glfwSetWindowIcon` protocol crashes on X11 and Wayland/XWayland sessions.
- **Native OSS Audio:** Automatically sets `ALSOFT_DRIVERS=oss` for `openal-soft`.
- **FreeBSD Hardware Detection:** Implements native RAM (`sysctl hw.physmem`) and GPU (`pciconf`) discovery.

## Quick Install (FreeBSD 15 amd64)
Download `bsdprism-1.0.pkg` from the **[Releases](https://github.com)** page and install it via `pkg`:
```sh
sudo pkg add bsdprism-1.0.pkg
```

## Building from Source
1. Install build and runtime dependencies:
```sh
sudo pkg install -y git cmake-core ninja pkgconf qt6-base qt6-5compat qt6-svg qt6-networkauth qt6-tools kf6-extra-cmake-modules kf6-karchive cmark scdoc quazip-qt6 tomlplusplus libqrencode openjdk8 openjdk17 lwjgl lwjgl3 glfw openal-soft
```
2. Configure and compile:
```sh
JAVA_HOME=/usr/local/openjdk17 cmake -B build -G Ninja   -DCMAKE_BUILD_TYPE=Release   -DJAVA_HOME=/usr/local/openjdk17   -DLauncher_APP_BINARY_NAME="bsdprism"   -DLauncher_DISPLAY_NAME="BSDprism"   -DLauncher_BUILD_PLATFORM="freebsd"   -DBUILD_TESTING=OFF   -DENABLE_LTO=OFF
nice -n 20 ninja -C build -j 1
```

## License
Licensed under the **GNU General Public License v3.0 (GPL-3.0)**. See `LICENSE` for details.
