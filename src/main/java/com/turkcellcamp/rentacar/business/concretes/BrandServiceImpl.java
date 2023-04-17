package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateBrandRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateBrandResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateBrandResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllBrandsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetBrandResponse;
import com.turkcellcamp.rentacar.business.rules.BrandBusinessRules;
import com.turkcellcamp.rentacar.entities.Brand;
import com.turkcellcamp.rentacar.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandBusinessRules rules;
    private final ModelMapper mapper;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        List<Brand> brandList = brandRepository.findAll();
        List<GetAllBrandsResponse> response = brandList
                .stream()
                .map(brand -> mapper.map(brand, GetAllBrandsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(int id) {
        rules.checkIfBrandExistsById(id);
        Brand brand =  brandRepository.findById(id).orElseThrow();
        GetBrandResponse response = mapper.map(brand, GetBrandResponse.class);
        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        rules.checkIfBrandExistsByName(request.getName());
        Brand brand = mapper.map(request, Brand.class);
        brand.setId(0);
        brandRepository.save(brand);
        CreateBrandResponse response = mapper.map(brand, CreateBrandResponse.class);
        return response;
    }

    @Override
    public UpdateBrandResponse update(int id, UpdateBrandRequest request) {
        rules.checkIfBrandExistsById(id);
        Brand brand = mapper.map(request, Brand.class);
        brand.setId(id);
        brandRepository.save(brand);
        UpdateBrandResponse response = mapper.map(brand, UpdateBrandResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfBrandExistsById(id);
        brandRepository.deleteById(id);
    }
}
