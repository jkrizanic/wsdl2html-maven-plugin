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

package hr.jkrizanic.maven.plugin.wsdl2html.constants;

/**
 * This class represents constants used in this plugin!
 * 
 * @author jkrizanic
 */
public class ConstantsWsdl2Html {
    /**
     * This array represents possible paths for schema inclusion in a WSDL file.
     */
    public static final String[] SCHEMA_LOCATION_PATHS = {
            "declare namespace s='http://schemas.xmlsoap.org/wsdl/' .//s:import/@location",
            "declare namespace s='http://www.w3.org/2001/XMLSchema' .//s:import/@schemaLocation",
            "declare namespace s='http://www.w3.org/2001/XMLSchema' .//s:include/@schemaLocation"};
}
