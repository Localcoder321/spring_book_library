package dev.localcoder.springbooklibrary.security;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;
import dev.localcoder.springbooklibrary.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class RentalServiceProxy implements RentalService {

    private final RentalService rentalService;
    private final JwtService jwtService;

    private Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void ensureAuthenticated() {
        Authentication a = auth();
        if(a == null || !a.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }
    }

    @Override
    public RentalResponse takeBook(TakeBookRequest request) {
        ensureAuthenticated();
        return rentalService.takeBook(request);
    }

    @Override
    public RentalResponse returnBook(ReturnBookRequest book) {
        ensureAuthenticated();
        return rentalService.returnBook(book);
    }

    @Override
    public List<RentalResponse> getAllRentals() {
        ensureAuthenticated();
        Authentication a = auth();
        boolean isAdmin = a.getAuthorities().stream()
                .anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"));
        if(!isAdmin) {
            String email = (String) a.getPrincipal();
            throw new SecurityException("Only admin can view all rentals");
        }
        return rentalService.getAllRentals();
    }
}
