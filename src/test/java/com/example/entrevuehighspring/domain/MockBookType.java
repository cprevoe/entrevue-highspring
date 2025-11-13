package com.example.entrevuehighspring.domain;

import lombok.Getter;

/**
 * The Type of book (e.g. first volume of Encyclopedia Britanica, ISBN number, author, etc)
 * 
 * Books are copies of books with a shared type.
 */
public class MockBookType implements BookType {
    @Getter private BookTypeId id;    
}
