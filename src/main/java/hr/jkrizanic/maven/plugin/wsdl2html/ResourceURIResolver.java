package hr.jkrizanic.maven.plugin.wsdl2html;

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

import java.io.File;
import java.io.InputStream;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * Implementation of URI resolver for XSD location.
 *
 * @author jkrizanic
 */
public class ResourceURIResolver implements URIResolver {
    /**
     * Resource folder filed.
     */ 
    private final String resourceFolder;

    /**
     * Used for instantiating.
     * @param resourceFolder 
     */
    public ResourceURIResolver(String resourceFolder) {
        this.resourceFolder = resourceFolder;
    }

    /**
     * Own implementation of URIResolver which builds a new source.
     *
     * @param href
     * @param base
     * @return Returns new Source object, depending of loading from classpath!
     * @throws TransformerException
     */
    @Override
    public Source resolve(String href, String base) throws TransformerException {
        //If absolute path is configured.
        if (href != null && (href.contains("://") || href.startsWith("file:"))) {
            return new StreamSource(href);
        } else { 
            //If relative path is configured
            File file = new File(href);
            if (file.exists()) {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(href);
                if (is != null) {
                    return new StreamSource(is);
                }
            }            
            return new StreamSource(this.resourceFolder + "/" + href);
        }
    }
}
