package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;

import java.util.List;

public interface RentalService {
    RentalResponse takeBook(TakeBookRequest request);
    RentalResponse returnBook(ReturnBookRequest book);
    List<RentalResponse> getAllRentals();
}
