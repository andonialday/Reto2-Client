/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.cypher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author 2dam
 */
public class EncryptAsim {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private static PublicKey pubKey;

    private static void pub() {
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(ResourceBundle.getBundle("reto2g1cclient.properties.config").getString("PUBLICKEYPATH")));
            String pubK = reader.readLine();
            LOGGER.info("Clave publica leida");
            X509EncodedKeySpec x5 = new X509EncodedKeySpec(DatatypeConverter.parseHexBinary(pubK));
            pubKey = KeyFactory.getInstance("RSA").generatePublic(x5);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EncryptAsim.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(EncryptAsim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static String encryption(String plainText){
        pub();
        String res = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            
            res = DatatypeConverter.printHexBinary(cipher.doFinal(plainText.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncryptAsim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
}
