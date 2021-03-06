package study.devmeetingstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.study.AddressService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AddressControllerUnitTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @Mock
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    private Member loginMember;

    private TokenProvider tokenProvider;

    @BeforeEach
    void init(){
        tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
        // ?????? ArgumentResolver ??????.
        mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .addFilter((((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })))
                .setCustomArgumentResolvers(new MemberDecodeResolver(tokenProvider, memberRepository))
                .build();
        loginMember = createMember(1L, "dltmddn@na.na", "nick1");
    }

    private Member createMember(Long id, String email, String nickname){
        return Member.builder()
                .authority(Authority.ROLE_USER)
                .email(email)
                .password("123456")
                .status(MemberStatus.ACTIVE)
                .grade(0)
                .nickname(nickname)
                .id(id)
                .build();
    }

    @DisplayName("?????? ?????? 201 created")
    @Test
    void saveAddress() throws Exception{
        //given
        // ?????? ?????? ??? ??????
        TokenDto tokenDto = getTokenDto();

        AddressReqDto addressReqDto = new AddressReqDto("?????????","?????????","?????????");
        Address expectedAddress = createAddress(addressReqDto);
        doReturn(expectedAddress).when(addressService).saveAddress(any(AddressReqDto.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/addresses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addressReqDto)));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONObject data = (JSONObject) getDataOfJSON(body);
        Address address = Address.builder()
                .id(1L)
                .address1((String) data.get("address1"))
                .address2((String) data.get("address2"))
                .address3((String) data.get("address3"))
                .build();

        assertEquals(expectedAddress, address);
    }

    private TokenDto getTokenDto() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember ???????????? token ??????
        return tokenProvider.generateTokenDto(token);
    }

    private Object getDataOfJSON(String body) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(body);
        return json.get("data");
    }

    @DisplayName("?????? ?????? 200 Ok")
    @Test
    void getAddress() throws Exception{
        //given
        // ?????? ?????? ??? ??????
        TokenDto tokenDto = getTokenDto();

        AddressReqDto addressReqDto = new AddressReqDto("?????????", "?????????", "?????????");
        Address expectedAddress = createAddress(addressReqDto);
        doReturn(expectedAddress).when(addressService).findAddressById(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/addresses/" + expectedAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONObject data = (JSONObject) getDataOfJSON(body);
        Address address = Address.builder()
                .id(1L)
                .address1((String) data.get("address1"))
                .address2((String) data.get("address2"))
                .address3((String) data.get("address3"))
                .build();

        assertEquals(expectedAddress,  address);
    }

    private Address createAddress(AddressReqDto addressReqDto){
        return Address.builder()
                .id(1L)
                .address1(addressReqDto.getAddress1())
                .address2(addressReqDto.getAddress2())
                .address3(addressReqDto.getAddress3())
                .build();
    }
}

