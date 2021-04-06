
package javaanpr.recognizer;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerException;
//import org.xml.sax.SAXException;
//import javaanpr.configurator.Configurator;
import javaanpr.intelligence.Intelligence;
//import javaanpr.gui.ReportGenerator;
import javaanpr.imageanalysis.Char;
import javaanpr.neuralnetwork.NeuralNetwork;
//import javaanpr.neuralnetwork.NeuralNetwork.SetOfIOPairs;

public class NeuralPatternClassificator extends CharacterRecognizer {
    
    private static int normalize_x =
            Intelligence.configurator.getIntProperty("char_normalizedcurrentvalue_x");
    private static int normalize_y =
            Intelligence.configurator.getIntProperty("char_normalizedcurrentvalue_y");
    
    // rozmer vstupneho pismena po transformacii : 10 x 16 = 160 neuronov
    
    public NeuralNetwork network;
    
    // do not learn netwotk, but load if from file (default)
    public NeuralPatternClassificator() throws Exception {
        this(false);
    }
    
    public NeuralPatternClassificator(boolean learn) throws Exception {
        
        
        Vector<Integer> dimensions = new Vector<Integer>();
        
        // determine size of input layer according to chosen feature extraction method.
        int inputLayerSize;
        if (Intelligence.configurator.getIntProperty("char_featuresExtractionMethod")==0)
            inputLayerSize = normalize_x * normalize_y;
        else inputLayerSize = CharacterRecognizer.features.length*4;
        
        // construct new neural network with specified dimensions.
        dimensions.add(inputLayerSize);
        dimensions.add(Intelligence.configurator.getIntProperty("neural_topology"));
        dimensions.add(CharacterRecognizer.alphabet.length);
        this.network = new NeuralNetwork(dimensions);
        
        if (learn) {
            // learn network
            learnAlphabet(Intelligence.configurator.getStrProperty("char_learnzscore"));
        } else {
            // or load network from xml
            this.network = new NeuralNetwork(Intelligence.configurator.getPathProperty("char_neuralNetworkPath"));
        }
    }
    
    // IMAGE -> CHAR
    public RecognizedChar recognize(Char imgChar) { // rozpozna UZ normalizovany char
        imgChar.normalize();
        Vector<Double> output = this.network.test(imgChar.extractFeatures());
        double max = 0.0;
        int indexMax = 0;
        
        RecognizedChar recognized = new RecognizedChar();
        
        for (int i=0; i<output.size(); i++) {
            recognized.addPattern(recognized.new RecognizedPattern(this.alphabet[i], output.elementAt(i).floatValue()));
        }
        recognized.render();
        recognized.sort(1);
        return recognized;
    }

    public NeuralNetwork.SetOfIOPairs.IOPair createNewPair(char chr, Char imgChar) { // uz normalizonvany
        Vector<Double> vectorInput = imgChar.extractFeatures();
        
        
        
        Vector<Double> vectorOutput = new Vector<Double>();
        for (int i=0; i<this.alphabet.length; i++)
            if (chr == this.alphabet[i]) vectorOutput.add(1.0); else vectorOutput.add(0.0);
        
