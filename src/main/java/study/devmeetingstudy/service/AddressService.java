package study.devmeetingstudy.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.AddressNotFoundException;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.dto.address.AddressRequestDto;
import study.devmeetingstudy.repository.AddressRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    public Address store(AddressRequestDto addressRequestDto){

        return null;
    }

    public Address findAddress(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("해당 id로 주소를 찾을 수 없습니다"));
    }
}
