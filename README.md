wsdl2html-maven-plugin
======================

This repository contains the source of wsdl2html Maven plugin.
The plugin is developed by Jurica Krizanic.

The plugin is used for generating the documentation in HTML format from 
<documentation> tags in a WSDL file.  
For the transformation, the plugin uses the transformation available at 
http://tomi.vanek.sk/xml/wsdl-viewer.xsl !


Usage of the plugin is possible in 2 ways:
1. Generate documentation for declared WSDL files
2. Generate documentation for all WSDL files in provided directory

1. Generate documentation for declared WSDL files

<plugin>
     <groupId>hr.jkrizanic.maven</groupId>
     <artifactId>wsdl2html-maven-plugin</artifactId>
     <version>0.0.1</version>
     <configuration>
           <wsdlDirectory>${basedir}/src/main/resources/wsdl</wsdlDirectory>
           <wsdlFileNames>
               <wsdlFileName>Hello.wsdl</wsdlFileName>
           </wsdlFileNames>
           <outputDirectory>${basedir}/target/doc</outputDirectory>                   
      </configuration>
      <executions>
         <execution>
             <phase>package</phase>
                 <goals>
                    <goal>create</goal>
                 </goals>
         </execution>
      </executions>                
</plugin>

2. Generate documentation for all WSDL files in provided directory.
<plugin>
     <groupId>hr.jkrizanic.maven</groupId>
     <artifactId>wsdl2html-maven-plugin</artifactId>
     <version>0.0.1</version>
     <configuration>
           <wsdlDirectory>${basedir}/src/main/resources/wsdl</wsdlDirectory>
           <outputDirectory>${basedir}/target/doc</outputDirectory>                   
      </configuration>
      <executions>
         <execution>
             <phase>package</phase>
                 <goals>
                    <goal>create</goal>
                 </goals>
         </execution>
      </executions>                
</plugin>

Change log:

v0.0.1 (Jan 19, 2014):
* initial version of the plugin


