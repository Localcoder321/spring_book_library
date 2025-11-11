package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.entity.BookEntity;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.entity.RentalEntity;
import dev.localcoder.springbooklibrary.exception.ConflictException;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.repository.BookRepository;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import dev.localcoder.springbooklibrary.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceImplTest {
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReaderRepository readerRepository;
    @InjectMocks
    private RentalServiceImpl rentalService;
    private ReaderEntity sampleReader;
    private BookEntity sampleBook;

    @BeforeEach
    void setup() {
        sampleReader = new ReaderEntity(2L, "Ivan", "ivan@example.com", Instant.now());
        sampleBook = new BookEntity(3L, "Test book", "Author", 2020, "genre", true);
    }

    @Test
    void takeBook_success() {
        when(readerRepository.findById(2L)).thenReturn(Optional.of(sampleReader));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(sampleBook));
        when(rentalRepository.existsByBookAndReturnedOnIsNull(sampleBook)).thenReturn(false);
        when(rentalRepository.findByReaderAndReturnedOnIsNull(sampleReader)).thenReturn(List.of());

        ArgumentCaptor<RentalEntity> rentalCaptor = ArgumentCaptor.forClass(RentalEntity.class);
        when(rentalRepository.save(rentalCaptor.capture())).thenAnswer(invocation -> {
            RentalEntity rental = invocation.getArgument(0);
            rental.setId(100L);
            return rental;
        });

        ArgumentCaptor<BookEntity> bookCaptor = ArgumentCaptor.forClass(BookEntity.class);
        when(bookRepository.save(bookCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        TakeBookRequest request = new TakeBookRequest();
        request.setReaderId(2L);
        request.setBookId(3L);
        request.setDueOn(Instant.now().plusSeconds(60*60*24));

        RentalResponse response = rentalService.takeBook(request);
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(3L, response.getBookId());
        assertEquals(2L, response.getReaderId());
        assertNull(response.getReturnedOn());

        BookEntity savedBook = bookCaptor.getValue();
        assertFalse(savedBook.isAvailable(), "Книга должна стать недоступной после выдачи");
        RentalEntity savedRental = rentalCaptor.getValue();
        assertEquals(3L, savedRental.getBook().getId());
        assertEquals(2L, savedRental.getReader().getId());
        assertNotNull(savedRental.getTakenOn());

        verify(rentalRepository, times(1)).save(any(RentalEntity.class));
        verify(bookRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    void takeBook_WhenReachTheLimit() {
        when(readerRepository.findById(2L)).thenReturn(Optional.of(sampleReader));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(sampleBook));
        when(rentalRepository.existsByBookAndReturnedOnIsNull(sampleBook)).thenReturn(false);

        List<RentalEntity> books = new ArrayList<>();
        for(int i = 0; i <5; i++) {
            books.add(new RentalEntity(
                    (long) i, sampleBook, sampleReader, Instant.now(),
                    Instant.now().plusSeconds(10000), null));
        }
        when(rentalRepository.findByReaderAndReturnedOnIsNull(sampleReader)).thenReturn(books);

        TakeBookRequest request = new TakeBookRequest();
        request.setReaderId(2L);
        request.setBookId(3L);
        request.setDueOn(Instant.now().plusSeconds(60*60*24));

        ConflictException exception = assertThrows(ConflictException.class, () -> rentalService.takeBook(request));
        assertTrue(exception.getMessage().toLowerCase().contains("5")
                || exception.getMessage().toLowerCase().contains("5 active")
                || exception.getMessage().toLowerCase().contains("5 активных"));
    }

    @Test
    void takeBook_WhenBookIsNotAvailable() {
        BookEntity book = new BookEntity(3L, "Testing book", "Tester", 2024, "Test", false);
        when(readerRepository.findById(2L)).thenReturn(Optional.of(sampleReader));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));

        TakeBookRequest request = new TakeBookRequest();
        request.setBookId(3L);
        request.setReaderId(2L);
        request.setDueOn(Instant.now().plusSeconds(60*60*24));
        assertThrows(ConflictException.class, () -> rentalService.takeBook(request));
    }

    @Test
    void returnBook_Success() {
        BookEntity b = new BookEntity(2L, "T", "A", 2020, "g", false);
        ReaderEntity r = new ReaderEntity(1L, "Ivan", "ivan@example.com", Instant.now());
        RentalEntity rental = new RentalEntity(5L, b, r, Instant.now().minusSeconds(10000), Instant.now().plusSeconds(10000), null);

        when(rentalRepository.findById(5L)).thenReturn(Optional.of(rental));
        ArgumentCaptor<RentalEntity> rentalCaptor = ArgumentCaptor.forClass(RentalEntity.class);
        when(rentalRepository.save(rentalCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<BookEntity> bookCaptor = ArgumentCaptor.forClass(BookEntity.class);
        when(bookRepository.save(bookCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        ReturnBookRequest request = new ReturnBookRequest();
        request.setRentalId(5L);
        var response = rentalService.returnBook(request);
        assertNotNull(response);
        RentalEntity savedRental = rentalCaptor.getValue();
        assertNotNull(savedRental.getReturnedOn(), "returnedOn должен быть заполнен");
        BookEntity savedBook = bookCaptor.getValue();
        assertTrue(savedBook.isAvailable(), "Книга должна стать доступной после возврата");

        verify(rentalRepository, times(1)).save(any(RentalEntity.class));
        verify(bookRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    void returnBook_WhenAlreadyReturned() {
        BookEntity b = new BookEntity(2L, "T", "A", 2020, "g", true);
        ReaderEntity r = new ReaderEntity(1L, "Ivan", "ivan@example.com", Instant.now());
        RentalEntity rental = new RentalEntity(5L, b, r, Instant.now().minusSeconds(10000), Instant.now().plusSeconds(10000), Instant.now().minusSeconds(100));
        when(rentalRepository.findById(5L)).thenReturn(Optional.of(rental));

        ReturnBookRequest request = new ReturnBookRequest();
        request.setRentalId(5L);
        assertThrows(ConflictException.class, () -> rentalService.returnBook(request));
    }

    @Test
    void takeBook_WhenReaderNotFound() {
        when(readerRepository.findById(99L)).thenReturn(Optional.empty());

        TakeBookRequest request = new TakeBookRequest();
        request.setReaderId(99L);
        request.setBookId(2L);
        request.setDueOn(Instant.now().plusSeconds(10000));

        assertThrows(NotFoundException.class, () -> rentalService.takeBook(request));
    }

    @Test
    void returnBook_WhenRentalNotFound() {
        when(rentalRepository.findById(99L)).thenReturn(Optional.empty());

        ReturnBookRequest request = new ReturnBookRequest();
        request.setRentalId(99L);

        assertThrows(NotFoundException.class, () -> rentalService.returnBook(request));
    }
}
