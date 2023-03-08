echo "Extracting jar for docker image ..."
rm -rf target
mkdir -p target/dependency
cp deployment/*.jar target/dependency

cd target/dependency
jar -xf *.jar
rm *.jar

mv BOOT-INF/lib .

echo "Extracting jar for docker image - DONE"
echo "Copy application.properties"
mv BOOT-INF/classes/application.properties .
mv BOOT-INF/classes/licenses/ .
mv BOOT-INF/classes/fonts/ .
mv BOOT-INF/classes/images/ .

echo "Remove all *.properties and *.json"
rm -rf BOOT-INF/classes/*.properties

echo "Move files in classes into root"
mv BOOT-INF/classes/ch .

echo "Remove useless folders"
rm -rf org
rm -rf BOOT-INF
