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

import java.io.File;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Wsdl2HtmlMojo class.
 * 
 * @author jkrizanic
 */
public class TestWsdl2HtmlMojo{

    public TestWsdl2HtmlMojo() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Tests the case when WSDL and XSD schema are in the same folder.
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    @Test
    public void testWSDLAndXSDInSameFolder() throws MojoExecutionException, MojoFailureException {
        Wsdl2HtmlMojo mojo = new Wsdl2HtmlMojo();
        mojo.setWsdlDirectory("src/test/resources");
        mojo.setWsdlFileNames(new String[]{"Hello.wsdl"});

        mojo.setOutputDirectory("target/doc");
        mojo.execute();

        Assert.assertTrue(new File("target/doc/Hello.html").exists());
    }

    /**
     * Tests the case when the XSD schema is in a folder located in the WSDL's folder.
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    @Test
    public void testXSDInFolderLocatedInWSDLFolder() throws MojoExecutionException, MojoFailureException {
        Wsdl2HtmlMojo mojo = new Wsdl2HtmlMojo();
        mojo.setWsdlDirectory("src/test/resources");
        mojo.setWsdlFileNames(new String[]{"Hello1.wsdl"});
        mojo.setOutputDirectory("target/doc");
        mojo.execute();

        Assert.assertTrue(new File("target/doc/Hello1.html").exists());
    }

    /**
     * Tests the case when XSD schema is defined through absolute path.
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    @Test
    public void testXSDAbsolutePath() throws MojoExecutionException, MojoFailureException {
        Wsdl2HtmlMojo mojo = new Wsdl2HtmlMojo();
        mojo.setWsdlDirectory("src/test/resources");
        mojo.setWsdlFileNames(new String[]{"Hello2.wsdl"});
        mojo.setOutputDirectory("target/doc");
        mojo.execute();

        Assert.assertTrue(new File("target/doc/Hello2.html").exists());
    }

    /**
     * Tests the case of generating documentation for all WSDL files 
     * in the provided folders.
     * 
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    @Test
    public void testMultipleWsdlFiles() throws MojoExecutionException, MojoFailureException {
        Wsdl2HtmlMojo mojo = new Wsdl2HtmlMojo();
        mojo.setWsdlDirectory("src/test/resources");
        mojo.setOutputDirectory("target/doc");
        mojo.execute();

        Assert.assertTrue(new File("target/doc/Hello.html").exists());
        Assert.assertTrue(new File("target/doc/Hello1.html").exists());
        Assert.assertTrue(new File("target/doc/Hello2.html").exists());
    }
    
   /**
     * Tests the case when WSDL and XSD schema are in the same folder.
     * @throws MojoExecutionException
     * @throws MojoFailureException 
     */
    @Test
    public void testWSDLAndXSDInSameFolder_ExternalTemplate() throws MojoExecutionException, MojoFailureException {
        Wsdl2HtmlMojo mojo = new Wsdl2HtmlMojo();
        mojo.setWsdlDirectory("src/test/resources");
        mojo.setWsdlFileNames(new String[]{"Hello.wsdl"});
        mojo.setTemplatePath("src/test/resources/wsdl-viewer-ext.xsl");

        mojo.setOutputDirectory("target/doc");
        mojo.execute();

        Assert.assertTrue(new File("target/doc/Hello.html").exists());
    }
}
