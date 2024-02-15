all:
# the --server=false flag helps improve performance on LabTS by avoiding
# downloading the build-server "bloop".
# the --jvm system flag helps improve performance on LabTS by preventing
# scala-cli from downloading a whole jdk distribution on the lab machine
# the --force flag ensures that any existing built compiler is overwritten
# the --power flag is needed as `package` is an experimental "power user" feature
	make clean;
	scala-cli --power package . --server=false --jvm system --force -o wacc-compiler;
	cd test && bash build.sh && cd ..

clean:
	scala-cli clean . && rm -f wacc-compiler

frontend-integration-tests:
	cd test && bash build.sh frontend integration && cd ..

frontend-unit-tests:
	cd test && bash build.sh frontend unit && cd ..

backend-integration-tests:
	cd test && bash build.sh backend integration && cd ..

backend-unit-tests:
	cd test && bash build.sh backend unit && cd ..

.PHONY: all clean
