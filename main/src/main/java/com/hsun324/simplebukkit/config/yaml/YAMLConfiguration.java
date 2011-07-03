package com.hsun324.simplebukkit.config.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.LineBreak;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class YAMLConfiguration extends Configuration
{
    private Yaml yaml;
    private File file;

	public YAMLConfiguration(File file)
	{
		super(file);

        DumperOptions options = new DumperOptions();
        options.setIndent(4);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setLineBreak(LineBreak.WIN);

        yaml = new Yaml(new SafeConstructor(), new SCOMRepresenter(), options);

        this.file = file;
	}
	
    @Override
    public void load()
    {        
        FileInputStream stream = null;

        try
        {
            stream = new FileInputStream(file);
            nullRead(yaml.load(new UnicodeReader(stream)));
        }
        catch (IOException e)
        {
            root = new HashMap<String, Object>();
        }
        catch (ConfigurationException e)
        {
            root = new HashMap<String, Object>();
        }
        finally
        {
            try
            {
                if (stream != null)
                    stream.close();
            }
            catch (IOException e) { }
        }
    }
    
    @Override
    public boolean save()
    {
        FileOutputStream stream = null;
        File parent = file.getParentFile();
        if (parent != null)
            parent.mkdirs();
        try
        {
            stream = new FileOutputStream(file);
            yaml.dump(root, new OutputStreamWriter(stream, "UTF-8"));
            return true;
        }
        catch (IOException e) { }
        finally
        {
            try
            {
                if (stream != null)
                    stream.close();
            }
            catch (IOException e) { }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private void nullRead(Object input) throws ConfigurationException
    {
        try
        {
            if ( null == input )
                root = new HashMap<String, Object>();
            else
                root = (Map<String, Object>)input;
        }
        catch (ClassCastException e)
        {
            throw new ConfigurationException("Root document must be an key-value structure");
        }
    }
}
class SCOMRepresenter extends Representer
{

    public SCOMRepresenter()
    {
        super();
        this.nullRepresenter = new EmptyRepresentNull();
    }

    protected class EmptyRepresentNull implements Represent
    {
        public Node representData(Object data)
        {
            return representScalar(Tag.NULL, ""); //Changed "null" to "" so as to avoid writing nulls
        }
    }

    // Borrowed Code: SnakeYAML from http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue60/SkipBeanTest.java
    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        Node valueNode = tuple.getValueNode();
        if (valueNode instanceof CollectionNode)
        {
            if (Tag.SEQ.equals(valueNode.getTag()))
            {
                SequenceNode seq = (SequenceNode) valueNode;
                if (seq.getValue().isEmpty())
                    return null;
            }
            if (Tag.MAP.equals(valueNode.getTag()))
            {
                MappingNode seq = (MappingNode) valueNode;
                if (seq.getValue().isEmpty())
                    return null;
            }
        }
        return tuple;
    }
    // Borrowed Code: Stop
}
