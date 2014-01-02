package com.excilys.computerdatabase.validator;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.validator
 * User: lortola
 * Date: 25/11/13
 * Description: N/A
 */
public interface Validator<E> {
    public int validate(E obj);
}
