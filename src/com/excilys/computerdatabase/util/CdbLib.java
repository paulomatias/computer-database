package com.excilys.computerdatabase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.util
 * User: lortola
 * Date: 18/11/13
 * Description: N/A
 */
public class CdbLib {

    public static Logger logger = LoggerFactory.getLogger(CdbLib.class);

    public static int bitwiseAnd(int a, int b) {
        return a & b;
    }

}
