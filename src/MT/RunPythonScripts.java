/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunPythonScripts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String prg = "import sys";
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("C:\\tensorflow-gpu\\models\\research\\object_detection\\Object_detection_webcam.py"));
            out.write(prg);
            out.close();
            Process p = null;
            p = Runtime.getRuntime().exec("python C:\\tensorflow-gpu\\models\\research\\object_detection\\Object_detection_webcam.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = null;
            ret = in.readLine();

            System.out.println("value is : " + ret);
        } catch (IOException ex) {
            Logger.getLogger(RunPythonScripts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
