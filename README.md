thrift-archetype-quickstart
===========================

* To install the archetype in a local Maven repository:

```bash
    $ cd thrift-archetype-quickstart
    $ mvn install
    $ cd ..
```

* To create a new quickstart Thrift project:

```bash
    $ mvn archetype:generate \    
        -DarchetypeGroupId=com.nardoz \        
        -DarchetypeArtifactId=thrift-archetype-quickstart \
        -DarchetypeVersion=1.0.0 \
        -DgroupId=com.mycompany \
        -DartifactId=myapp \
        -Dversion=1.0-SNAPSHOT
    $ cd myapp
    $ mvn test
```