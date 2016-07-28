# 此脚本定义以下环境变量
#
# WX_HOME: 指向工程根目录或 tar 包解开后的目录
# WX_CLASSPATH: 指向运行时所需的 JAR 包
# WX_JAVA_OPTS: 运行命令行工具所需的配置

this="${BASH_SOURCE-$0}"
while [ -h "$this" ]; do
  ls=`ls -ld "$this"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    this="$link"
  else
    this=`dirname "$this"`/"$link" 
  fi  
done  
    
# convert relative path to absolute path
bin=`dirname "$this"`
script=`basename "$this"` 
bin=`cd "$bin">/dev/null; pwd`
this="$bin/$script"

# 定义 WX_HOME
export WX_HOME=$(dirname "$bin")

# 定义 WX_CLASSPATH
if [ -d "$WX_HOME/target" ]; then
  cached_cp_file="$WX_HOME/target/cached_classpath.txt"
  if [ ! -f "$cached_cp_file" ]; then
    echo 'need to "mvn package" first' >&2
    exit 1
  fi
  cp=$(cat "$cached_cp_file")
else
  cp=$(find "$WX_HOME/lib" | grep 'jar$' | sort | paste -s -d ':')
fi
cp=$(echo $cp | sed -e 's/:[^:]*google-collections-[0-9\.]*\.jar//g')

# 定义 WG_CONF_DIR
if [ "$WG_CONF_DIR" = "" ]; then
  export WG_CONF_DIR="$WG_HOME/conf"
fi

export WX_CLASSPATH="$cp:$WG_CONF_DIR"
#export WX_CLASSPATH="$cp"

# 定义 WX_JAVA_OPTS
debug_opts=""
if [ "$WX_DEBUG" = "1" -o "$WX_DEBUG" = "true" ]; then
  debug_opts="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"
fi
log4j_opts="-Dlog4j.configuration=wx-log4j.properties"
#http_opts="-Dhttp.keepAlive=true -Dhttp.maxConnections=128"
#native_opts="-Djava.library.path=$WX_HOME/lib/native"
export WX_JAVA_OPTS="-cp $WX_CLASSPATH:$WX_CLASSPATH_EXTRA $log4j_opts  $debug_opts"
#export WX_JAVA_OPTS=-cp $WX_CLASSPATH:$WX_CLASSPATH_EXTRA $log4j_opts $debug_opts $http_opts 

# 定义公用方法
# 参数: dir, regex
find_jar() {
  dir=$1
  regex=$2
  if [ ! -d $dir ]; then
    exit 1
  fi
  jar=$(find "$dir" | grep "$regex" | head -n1)
  if [ -z $jar ]; then
    exit 1
  fi
  if [ ! -f $jar ]; then
    exit 1
  fi
  echo $jar
}

# 参数: regex
find_jar_in_classpath() {
  regex=$1
  jar=$(echo $WX_CLASSPATH | tr ':' '\n' | grep "$regex" | head -n1)
  if [ -z $jar ]; then
    exit 1
  fi
  if [ ! -f $jar ]; then
    exit 1
  fi
  echo $jar
}

