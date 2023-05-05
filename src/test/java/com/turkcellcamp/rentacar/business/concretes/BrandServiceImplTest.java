package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateBrandRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateBrandResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllBrandsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetBrandResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateBrandResponse;
import com.turkcellcamp.rentacar.business.rules.BrandBusinessRules;
import com.turkcellcamp.rentacar.entities.Brand;
import com.turkcellcamp.rentacar.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BrandServiceImplTest {

    private BrandRepository brandRepository;
    private BrandBusinessRules brandBusinessRules;
    private ModelMapper mapper;
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        brandBusinessRules = mock(BrandBusinessRules.class);
        mapper = mock(ModelMapper.class);
        brandService = new BrandServiceImpl(brandRepository, brandBusinessRules, mapper);
    }

    @Test
    void shouldReturnGetAllBrandsResponse() {
        // arrange
        Brand brand1 = new Brand(1, "BMW", new ArrayList<>());
        Brand brand2 = new Brand(2, "Mercedes", new ArrayList<>());
        List<Brand> brandList = List.of(brand1, brand2);

        GetAllBrandsResponse response1 = new GetAllBrandsResponse(brand1.getId(), brand1.getName());
        GetAllBrandsResponse response2 = new GetAllBrandsResponse(brand2.getId(), brand2.getName());
        List<GetAllBrandsResponse> response = List.of(response1, response2);

        when(brandRepository.findAll()).thenReturn(brandList);
        when(mapper.map(brand1, GetAllBrandsResponse.class)).thenReturn(response1);
        when(mapper.map(brand2, GetAllBrandsResponse.class)).thenReturn(response2);

        // act
        List<GetAllBrandsResponse> actualResponse = brandService.getAll();

        // assert
        assertEquals(response, actualResponse);

        verify(brandRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnGetBrandResponse_whenBrandIdExists() {
        // arrange
        int id = 1;

        Brand brand = new Brand(id, "BMW", new ArrayList<>());

        GetBrandResponse response = new GetBrandResponse(brand.getId(), brand.getName());

        doNothing().when(brandBusinessRules).checkIfBrandExistsById(id);
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
        when(mapper.map(brand, GetBrandResponse.class)).thenReturn(response);

        // act
        GetBrandResponse actual = brandService.getById(1);

        // assert
        assertEquals(response, actual);

        verify(brandRepository, times(1)).findById(id);
    }

    @Test
    void shouldAddBrand_whenBrandNameNotExists() {
        // arrange
        CreateBrandRequest request = new CreateBrandRequest("BMW");

        Brand brand = new Brand();
        brand.setId(0);
        brand.setName(request.getName());

        CreateBrandResponse response = new CreateBrandResponse(brand.getId(), brand.getName());

        doNothing().when(brandBusinessRules).checkIfBrandExistsByName(request.getName());
        when(mapper.map(request, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(brand);
        when(mapper.map(brand, CreateBrandResponse.class)).thenReturn(response);

        // act
        CreateBrandResponse actualResponse = brandService.add(request);

        // assert
        assertEquals(response, actualResponse);

        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void shouldUpdateBrand_whenBrandIdExists() {
        // arrange
        int id = 1;

        UpdateBrandRequest request = new UpdateBrandRequest("BMW");

        Brand brand = new Brand(id, request.getName(), new ArrayList<>());

        UpdateBrandResponse response = new UpdateBrandResponse(brand.getId(), brand.getName());

        doNothing().when(brandBusinessRules).checkIfBrandExistsById(id);
        when(mapper.map(request, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(brand);
        when(mapper.map(brand, UpdateBrandResponse.class)).thenReturn(response);

        // act
        UpdateBrandResponse actualResponse = brandService.update(id, request);

        // assert
        assertEquals(response, actualResponse);

        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void shouldDeleteBrand_whenBrandIdExists() {
        // arrange
        int id = 1;

        doNothing().when(brandBusinessRules).checkIfBrandExistsById(id);
        doNothing().when(brandRepository).deleteById(id);

        // act
        brandService.delete(id);

        // assert
        verify(brandRepository, times(1)).deleteById(id);
    }
}
