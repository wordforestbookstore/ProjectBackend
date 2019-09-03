package com.eins.book.store.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Table(name = "bookstoredatabase.token_kind")
public class TokenKind {

    @Column(name = "token")
    private String token;

    @Column(name = "kind")
    private String kind;





    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
