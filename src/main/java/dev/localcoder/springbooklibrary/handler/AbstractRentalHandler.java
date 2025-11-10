package dev.localcoder.springbooklibrary.handler;

import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;

public abstract class AbstractRentalHandler {
    private AbstractRentalHandler next;

    public AbstractRentalHandler linkWith(AbstractRentalHandler next) {
        this.next = next;
        return next;
    }

    public void check(Book book, Reader reader) {
        if(!handle(book, reader)) {
            throw new RuntimeException("Rental validation failed at " + this.getClass().getSimpleName());
        }
        if(next != null) next.check(book, reader);
    }

    protected abstract boolean handle(Book book, Reader reader);
}
