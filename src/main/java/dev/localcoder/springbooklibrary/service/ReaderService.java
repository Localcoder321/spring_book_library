package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import dev.localcoder.springbooklibrary.dto.reader.RegisterReaderRequest;

import java.util.List;

public interface ReaderService {
    ReaderResponse register(RegisterReaderRequest request);
    ReaderResponse getById(Long id);
    List<ReaderResponse> getAll();
}
