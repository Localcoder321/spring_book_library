package dev.localcoder.springbooklibrary.controller;

import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import dev.localcoder.springbooklibrary.dto.reader.RegisterReaderRequest;
import dev.localcoder.springbooklibrary.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/readers")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<ReaderResponse> registerReader(@Valid @RequestBody RegisterReaderRequest request) {
        ReaderResponse created = readerService.register(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReaderResponse>> getAllReaders() {
        List<ReaderResponse> readers = readerService.getAll();
        return ResponseEntity.ok(readers);
    }
}
