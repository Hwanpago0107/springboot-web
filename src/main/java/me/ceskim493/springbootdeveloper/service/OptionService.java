package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Option;
import me.ceskim493.springbootdeveloper.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public Option save(Option option) throws Exception {
        return optionRepository.save(option);
    }

    public List<Option> findAllByItem_id(Long item_id) {
        return optionRepository.findAllByItem_Id(item_id);
    }
}
