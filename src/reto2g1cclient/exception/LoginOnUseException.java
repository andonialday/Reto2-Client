package reto2g1cclient.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom trigger for unavailable user credentials on SignUp requests
 *
 * @author Enaitz Izagirre
 */
public class LoginOnUseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Generation of the customized Exception
     *
     * @param message
     */
    public LoginOnUseException(String message) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, message, this);
    }

}
