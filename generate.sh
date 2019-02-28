if [ $# -eq 0 ]; then
    echo "Please provide path to generate server data"
    exit 1
fi
mkdir -p $1
cd $1
rm *.*
cd ..
java -jar CPUUsageGenerator.jar $1
