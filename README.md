# xtimer by alishangtian.com

### start
> nohup java -Dspring.config.location=application.yml -Xmx4g -Xms2g -XX:+UseG1GC -verbose:gc -Xloggc:/home/work/log/xtimer.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintPromotionFailure -XX:+PrintGCApplicationStoppedTime -XX:+PrintHeapAtGC -jar xtimer-broker-v0.0.1.release.jar >/dev/null 2>&1 &
