package ru.site.mysite.mysite.Entitys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.site.mysite.mysite.MtsRepository;

@Service
public class MtsService {

    private MtsRepository mtsRepository;

    @Autowired
    public MtsService(MtsRepository _mtsRepository){
        this.mtsRepository = _mtsRepository;
    }

    public MtsDB saveImg(MtsDB mtsDB){
        return mtsRepository.save(mtsDB);

    }

    public void deleteImg(Long id){
        mtsRepository.delete(id);
    }
}
