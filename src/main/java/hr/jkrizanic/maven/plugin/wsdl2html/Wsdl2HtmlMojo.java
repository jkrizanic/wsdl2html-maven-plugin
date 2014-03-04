/*
The MIT License (MIT)

Copyright (c) 2014 Jurica Krizanic (jkrizanic(at)gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package hr.jkrizanic.maven.plugin.wsdl2html;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * A Mojo implementation of Wsdl2Html Maven plugin! Goal name is marked with the
 * name attribute of @Mojo annotation.
 *
 * @author jkrizanic
 */
@Mojo(name = "create")
public class Wsdl2HtmlMojo extends AbstractMojo {

    @Parameter(property = "wsdlDirectory")
    private String wsdlDirectory;

    @Parameter(property = "wsdlFileNames")
    private String[] wsdlFileNames;

    @Parameter(property = "outputDirectory", defaultValue = "target/doc")
    private String outputDirectory;
    
    @Parameter(property = "templatePath")
    private String templatePath;

    /**
     * The main execution method!
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Preparing to generate HTML document from WSDL...");
        if (this.wsdlDirectory == null) {
            throw new IllegalStateException("WSDL directory is mandatory parameter!");
        }

        try {
            //If wsdlDirectory is defined, and there is no wsdlFile names defined, perform on all files in directory.
            if (this.wsdlFileNames == null || this.wsdlFileNames.length == 0) {
                File file = new File(this.wsdlDirectory);
                //Get all files names in directory.
                String[] wsdlFiles = file.list();
                for (String wsdl : wsdlFiles) {
                    //Check if it is really a WSDL file in the directory. 
                    if (wsdl != null && (wsdl.endsWith("wsdl") || wsdl.endsWith("WSDL"))) {
                        generateDocumentationForWsdl(wsdl);
                    }
                }
                //No need to do generation for one defined WSDL.
                return;
            }

            //Do this for only one wsdl defined!
            if (this.wsdlFileNames != null) {
                for (String wsdlName : wsdlFileNames) {
                    generateDocumentationForWsdl(wsdlName);
                }
            }
            getLog().info("Finished generating HTML document from WSDL!");
        } catch (FileNotFoundException e) {
            getLog().error("Error occurred in generating HTML document!", e);
        } catch (TransformerException e) {
            getLog().error("Error occurred in generating HTML document!", e);
        }
    }

    /**
     * Concrete implementation of transforming a WSDL to HTML. This is called in
     * both cases
     *
     * @param wsdlName Name of the WSDL file.
     * @throws TransformerConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    private void generateDocumentationForWsdl(String wsdlName) throws TransformerConfigurationException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl",null);
        //Sets a custom ResourceURIResolver
        transformerFactory.setURIResolver(new ResourceURIResolver(this.wsdlDirectory));
        //Load the XSL transformation file from the classpath or from custom provided location
        InputStream xsl = getTransformerStream();
        StreamSource xslSource = new StreamSource(xsl);
        Transformer transformer = transformerFactory.newTransformer(xslSource);

        File fileOutputDirectory = new File(this.outputDirectory);
        //If output directory not exist, create it.
        if (!fileOutputDirectory.exists()) {
            fileOutputDirectory.mkdir();
        }

        StreamSource xmlSource = new StreamSource(this.wsdlDirectory + "/" + wsdlName);
        //Determine output document name (which is name of WSDL file with html extension.)
        String docName = wsdlName.replace(".wsdl", ".html");
        //Do the transformation
        transformer.transform(xmlSource, new StreamResult(
                new FileOutputStream(fileOutputDirectory.getPath() + "/" + docName)));
    }
    
    /**
     * Returns transformer file input stream depending on templatePath parameter.
     * If not defined, returns default embedded transformer file.
     * @return Returns input stream of transformer file.
     */
    private InputStream getTransformerStream(){
        try {
            if(this.templatePath == null){
                return this.getClass().getClassLoader().getResourceAsStream("wsdl-viewer.xsl");
            }
            return new FileInputStream(this.templatePath);
        } catch (FileNotFoundException ex) {
            getLog().error("External defined transformation template file not found! Returning default one!");
            return this.getClass().getClassLoader().getResourceAsStream("wsdl-viewer.xsl");
        }
    }
    
    

    protected void setWsdlFileNames(String[] wsdlFileNames) {
        this.wsdlFileNames = wsdlFileNames;
    }

    protected void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    protected void setWsdlDirectory(String wsdlDirectory) {
        this.wsdlDirectory = wsdlDirectory;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }    
}
