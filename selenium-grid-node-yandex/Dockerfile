FROM ubuntu:focal-20230301 as base
LABEL authors="Selenium <selenium-developers@googlegroups.com>"

#================================================
# Customize sources for apt-get
#================================================
RUN  echo "deb http://archive.ubuntu.com/ubuntu focal main universe\n" > /etc/apt/sources.list \
  && echo "deb http://archive.ubuntu.com/ubuntu focal-updates main universe\n" >> /etc/apt/sources.list \
  && echo "deb http://security.ubuntu.com/ubuntu focal-security main universe\n" >> /etc/apt/sources.list

# No interactive frontend during docker build
ENV DEBIAN_FRONTEND=noninteractive \
    DEBCONF_NONINTERACTIVE_SEEN=true

#========================
# Miscellaneous packages
# Includes minimal runtime used for executing non GUI Java programs
#========================
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    acl \
    bzip2 \
    ca-certificates \
    openjdk-11-jre-headless \
    tzdata \
    sudo \
    unzip \
    wget \
    jq \
    curl \
    supervisor \
    gnupg2 \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
  && sed -i 's/securerandom\.source=file:\/dev\/random/securerandom\.source=file:\/dev\/urandom/' ./usr/lib/jvm/java-11-openjdk-amd64/conf/security/java.security

#===================
# Timezone settings
# Possible alternative: https://github.com/docker/docker/issues/3359#issuecomment-32150214
#===================
ENV TZ "UTC"
RUN echo "${TZ}" > /etc/timezone \
  && dpkg-reconfigure --frontend noninteractive tzdata

#========================================
# Add normal user and group with passwordless sudo
#========================================
RUN groupadd seluser \
         --gid 1201 \
  && useradd seluser \
         --create-home \
         --gid 1201 \
         --shell /bin/bash \
         --uid 1200 \
  && usermod -a -G sudo seluser \
  && echo 'ALL ALL = (ALL) NOPASSWD: ALL' >> /etc/sudoers \
  && echo 'seluser:secret' | chpasswd
ENV HOME=/home/seluser

#======================================
# Add Grid check script
#======================================
COPY check-grid.sh entry_point.sh /opt/bin/
RUN sudo chmod +x /opt/bin/check-grid.sh /opt/bin/entry_point.sh
#======================================
# Add Supervisor configuration file
#======================================
COPY supervisord.conf /etc

#==========
# Selenium & relaxing permissions for OpenShift and other non-sudo environments
#==========
RUN  mkdir -p /opt/selenium /opt/selenium/assets /var/run/supervisor /var/log/supervisor \
  && touch /opt/selenium/config.toml \
  && chmod -R 777 /opt/selenium /opt/selenium/assets /var/run/supervisor /var/log/supervisor /etc/passwd \
  && wget --no-verbose https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.8.0/selenium-server-4.8.3.jar \
    -O /opt/selenium/selenium-server.jar \
  && wget --no-verbose https://repo1.maven.org/maven2/org/seleniumhq/selenium/selenium-http-jdk-client/4.8.3/selenium-http-jdk-client-4.8.3.jar \
    -O /opt/selenium/selenium-http-jdk-client.jar \
  && chgrp -R 0 /opt/selenium ${HOME} /opt/selenium/assets /var/run/supervisor /var/log/supervisor \
  && chmod -R g=u /opt/selenium ${HOME} /opt/selenium/assets /var/run/supervisor /var/log/supervisor \
  && setfacl -Rm u:seluser:rwx /opt /opt/selenium ${HOME} /opt/selenium/assets /var/run/supervisor /var/log/supervisor

#=====
# Download observability related jaegar jars and make them available in a separate directory
# so that the container can skip downloading them everytime it comes up
#=====
RUN curl -fLo /tmp/cs https://github.com/coursier/launchers/raw/master/coursier \
  && chmod +x /tmp/cs \
  && mkdir -p /external_jars \
  && chmod -R 775 /external_jars

RUN /tmp/cs fetch --classpath --cache /external_jars io.opentelemetry:opentelemetry-exporter-jaeger:1.22.0 io.grpc:grpc-netty:1.52.1 > /external_jars/.classpath.txt

RUN chmod 664 /external_jars/.classpath.txt

#===================================================
# Run the following commands as non-privileged user
#===================================================
USER 1200:1201

# Boolean value, maps "--bind-host"
ENV SE_BIND_HOST false

CMD ["/opt/bin/entry_point.sh"]




FROM base as node_base
USER root

#==============
# Xvfb
#==============
RUN apt-get update -qqy \
  && apt-get -qqy install \
    xvfb \
    pulseaudio \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#==============================
# Locale and encoding settings
#==============================
ENV LANG_WHICH en
ENV LANG_WHERE US
ENV ENCODING UTF-8
ENV LANGUAGE ${LANG_WHICH}_${LANG_WHERE}.${ENCODING}
ENV LANG ${LANGUAGE}
# Layer size: small: ~9 MB
# Layer size: small: ~9 MB MB (with --no-install-recommends)
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    language-pack-en \
    tzdata \
    locales \
  && locale-gen ${LANGUAGE} \
  && dpkg-reconfigure --frontend noninteractive locales \
  && apt-get -qyy autoremove \
  && rm -rf /var/lib/apt/lists/* \
  && apt-get -qyy clean

#=====
# VNC
#=====
RUN apt-get update -qqy \
  && apt-get -qqy install \
  x11vnc \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#=========
# fluxbox
# A fast, lightweight and responsive window manager
#=========
RUN apt-get update -qqy \
  && apt-get -qqy install \
    fluxbox \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#================
# Font libraries
#================
# libfontconfig            ~1 MB
# libfreetype6             ~1 MB
# xfonts-cyrillic          ~2 MB
# xfonts-scalable          ~2 MB
# fonts-liberation         ~3 MB
# fonts-ipafont-gothic     ~13 MB
# fonts-wqy-zenhei         ~17 MB
# fonts-tlwg-loma-otf      ~300 KB
# ttf-ubuntu-font-family   ~5 MB
#   Ubuntu Font Family, sans-serif typeface hinted for clarity
# Removed packages:
# xfonts-100dpi            ~6 MB
# xfonts-75dpi             ~6 MB
# fonts-noto-color-emoji   ~10 MB
# Regarding fonts-liberation see:
#  https://github.com/SeleniumHQ/docker-selenium/issues/383#issuecomment-278367069
# Layer size: small: 50.3 MB (with --no-install-recommends)
# Layer size: small: 50.3 MB
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    libfontconfig \
    libfreetype6 \
    xfonts-cyrillic \
    xfonts-scalable \
    fonts-liberation \
    fonts-ipafont-gothic \
    fonts-wqy-zenhei \
    fonts-tlwg-loma-otf \
    ttf-ubuntu-font-family \
    fonts-noto-color-emoji \
  && rm -rf /var/lib/apt/lists/* \
  && apt-get -qyy clean

########################################
# noVNC exposes VNC through a web page #
########################################
ENV NOVNC_VERSION="1.4.0" \
    WEBSOCKIFY_VERSION="0.11.0"
RUN  wget -nv -O noVNC.zip \
       "https://github.com/novnc/noVNC/archive/refs/tags/v${NOVNC_VERSION}.zip" \
  && unzip -x noVNC.zip \
  && mv noVNC-${NOVNC_VERSION} /opt/bin/noVNC \
  && cp /opt/bin/noVNC/vnc.html /opt/bin/noVNC/index.html \
  && rm noVNC.zip \
  && wget -nv -O websockify.zip \
      "https://github.com/novnc/websockify/archive/refs/tags/v${WEBSOCKIFY_VERSION}.zip" \
  && unzip -x websockify.zip \
  && rm websockify.zip \
  && rm -rf websockify-${WEBSOCKIFY_VERSION}/tests \
  && mv websockify-${WEBSOCKIFY_VERSION} /opt/bin/noVNC/utils/websockify

#=========================================================================================================================================
# Run this command for executable file permissions for /dev/shm when this is a "child" container running in Docker Desktop and WSL2 distro
#=========================================================================================================================================
RUN sudo chmod +x /dev/shm

#===================================================
# Run the following commands as non-privileged user
#===================================================

USER 1200

#==============================
# Scripts to run Selenium Node and XVFB
#==============================
COPY start-selenium-node.sh \
      start-xvfb.sh \
      /opt/bin/
RUN sudo chmod +x /opt/bin/start-selenium-node.sh /opt/bin/start-xvfb.sh
#==============================
# Supervisor configuration file
#==============================
COPY selenium.conf /etc/supervisor/conf.d/

#==============================
# Generating the VNC password as seluser
# So the service can be started with seluser
#==============================

RUN mkdir -p ${HOME}/.vnc \
  && x11vnc -storepasswd secret ${HOME}/.vnc/passwd

#==========
# Relaxing permissions for OpenShift and other non-sudo environments
#==========
RUN sudo chmod -R 775 ${HOME} \
  && sudo chgrp -R 0 ${HOME} \
  && sudo chmod -R g=u ${HOME}

#==============================
# Scripts to run fluxbox, x11vnc and noVNC
#==============================
COPY start-vnc.sh \
      start-novnc.sh \
      /opt/bin/
RUN sudo chmod +x /opt/bin/start-vnc.sh /opt/bin/start-novnc.sh
#==============================
# Selenium Grid logo as wallpaper for Fluxbox
#==============================
COPY selenium_grid_logo.png /usr/share/images/fluxbox/ubuntu-light.png

#============================
# Some configuration options
#============================
ENV SE_SCREEN_WIDTH 1360
ENV SE_SCREEN_HEIGHT 1020
ENV SE_SCREEN_DEPTH 24
ENV SE_SCREEN_DPI 96
ENV SE_START_XVFB true
ENV SE_START_VNC true
ENV SE_START_NO_VNC true
ENV SE_NO_VNC_PORT 7900
ENV SE_VNC_PORT 5900
ENV DISPLAY :99.0
ENV DISPLAY_NUM 99
# Path to the Configfile
ENV CONFIG_FILE=/opt/selenium/config.toml
ENV GENERATE_CONFIG true
# Drain the Node after N sessions. 
# A value higher than zero enables the feature
ENV SE_DRAIN_AFTER_SESSION_COUNT 0



#========================
# Selenium Configuration
#========================
# As integer, maps to "max-concurrent-sessions"
ENV SE_NODE_MAX_SESSIONS 1
# As integer, maps to "session-timeout" in seconds
ENV SE_NODE_SESSION_TIMEOUT 300
# As boolean, maps to "override-max-sessions"
ENV SE_NODE_OVERRIDE_MAX_SESSIONS false

# Following line fixes https://github.com/SeleniumHQ/docker-selenium/issues/87
ENV DBUS_SESSION_BUS_ADDRESS=/dev/null

# Creating base directory for Xvfb
RUN  sudo mkdir -p /tmp/.X11-unix && sudo chmod 1777 /tmp/.X11-unix

# Copying configuration script generator
COPY generate_config /opt/bin/generate_config
EXPOSE 5900

FROM node_base
USER root
RUN chmod +x /opt/bin/generate_config

ARG NODE_VERSION=12.18.4 \
    ARCH=x64 \
    YA_KEY=YANDEX-BROWSER-KEY.GPG \
    DRIVER_VERSION=23.3.1 \
    DRIVER_VPATCH=755  \
    YANDEX_BROWSER_VERSION=23.3.1.912-1
ARG DRIVER_NAME=yandexdriver-${DRIVER_VERSION}.${DRIVER_VPATCH}-linux.zip

RUN curl -sSL "https://repo.yandex.ru/yandex-browser/${YA_KEY}" | apt-key add - \
    && echo "deb https://repo.yandex.ru/yandex-browser/deb stable main" > /etc/apt/sources.list.d/yandex.list 
    
RUN curl --silent --show-error --location --remote-name "https://github.com/yandex/YandexDriver/releases/download/v${DRIVER_VERSION}-stable/${DRIVER_NAME}" \
    && unzip ${DRIVER_NAME} -d /opt/selenium \
    && rm ${DRIVER_NAME} \
    && mv /opt/selenium/yandexdriver /opt/selenium/yandexdriver-${DRIVER_VERSION} \
    && chmod 755 /opt/selenium/yandexdriver-${DRIVER_VERSION} \
    && ln -fs /opt/selenium/yandexdriver-${DRIVER_VERSION} /usr/bin/chromedriver \
    && ln -fs /opt/selenium/yandexdriver-${DRIVER_VERSION} /usr/bin/yandexdriver

RUN apt update --quiet && apt install --quiet --yes \
    ca-certificates \
    yandex-browser-stable=${YANDEX_BROWSER_VERSION}        \
    && apt purge --auto-remove -y xz-utils \
    && apt clean \
    && rm -rf /var/lib/apt/lists/* \
    && update-ca-certificates

COPY wrap_yandex_binary /opt/bin/wrap_yandex_binary
RUN sudo chmod +x /opt/bin/wrap_yandex_binary
RUN /opt/bin/wrap_yandex_binary

USER 1200

RUN echo "chrome" > /opt/selenium/browser_name
