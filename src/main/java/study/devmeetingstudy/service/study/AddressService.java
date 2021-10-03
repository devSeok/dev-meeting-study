package study.devmeetingstudy.service.study;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.AddressNotFoundException;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.repository.AddressRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(AddressReqDto addressReqDto){
        Address address = Address.create(addressReqDto);
        return addressRepository.save(address);
    }

    public Address findAddress(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("해당 id로 주소를 찾을 수 없습니다"));
    }

}
