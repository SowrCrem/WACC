all:
# the --server=false flag helps improve performance on LabTS by avoiding
# downloading the build-server "bloop".
# the --jvm system flag helps improve performance on LabTS by preventing
# scala-cli from downloading a whole jdk distribution on the lab machine
# the --force flag ensures that any existing built compiler is overwritten
# the --power flag is needed as `package` is an experimental "power user" feature
	make clean;
	scala-cli --power package . --server=false --jvm system --force -o wacc-compiler;
	cd test/integrationTests && bash buildIntegrationTests.sh && cd ../..

clean:
	scala-cli clean . && rm -f wacc-compiler && rm -rf test/integrationTests/*Tests/

integrationTests:
	cd test/integrationTests && bash buildIntegrationTests.sh && cd ../..

.PHONY: all clean
