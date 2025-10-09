package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import dev.localcoder.springbooklibrary.dto.reader.RegisterReaderRequest;

public interface ReaderService {
    ReaderResponse register(RegisterReaderRequest request);
    ReaderResponse getById(Long id);
}
