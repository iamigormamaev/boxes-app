
package tech.mamaev.boxes.model.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the tech.mamaev.xmltest.mypackage package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: tech.mamaev.xmltest.mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Storage }
     * 
     */
    public Storage createStorage() {
        return new Storage();
    }

    /**
     * Create an instance of {@link Storage.Box }
     * 
     */
    public Storage.Box createStorageBox() {
        return new Storage.Box();
    }

    /**
     * Create an instance of {@link Storage.Item }
     * 
     */
    public Storage.Item createStorageItem() {
        return new Storage.Item();
    }
}
