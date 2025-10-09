package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import dev.localcoder.springbooklibrary.dto.reader.RegisterReaderRequest;
import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.exception.ConflictException;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import dev.localcoder.springbooklibrary.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final RentalRepository rentalRepository;

    @Override
    public ReaderResponse register(RegisterReaderRequest request) {
        readerRepository.findByEmail(request.getEmail()).ifPresent(reader -> {
            throw new ConflictException("Reader with this email already exists");
        });

        Reader reader = new Reader();
        reader.setName(request.getName());
        reader.setEmail(request.getEmail());
        reader.setRegisteredOn(Instant.now());
        readerRepository.save(reader);
        return toResponse(reader);
    }

    @Override
    public ReaderResponse getById(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reader not found: " + id));
        ReaderResponse response = toResponse(reader);
        response.setRentals(
                rentalRepository.findByReaderAndReturnedOnIsNull(reader).stream().map(rental -> {
                    var rentalResponse = new RentalResponse();
                    rentalResponse.setId(rental.getId());
                    rentalResponse.setBookId(rental.getBook().getId());
                    rentalResponse.setBookTitle(rental.getBook().getTitle());
                    rentalResponse.setTakenOn(rental.getTakenOn());
                    rentalResponse.setDueOn(rental.getDueOn());
                    rentalResponse.setReturnedOn(rental.getReturnedOn());
                    return rentalResponse;
                }).collect(Collectors.toList())
        );
        return response;
    }

    private ReaderResponse toResponse(Reader reader) {
        ReaderResponse response = new ReaderResponse();
        response.setId(reader.getId());
        response.setName(reader.getName());
        response.setEmail(reader.getEmail());
        response.setRegisteredOn(reader.getRegisteredOn());
        return response;
    }
}
