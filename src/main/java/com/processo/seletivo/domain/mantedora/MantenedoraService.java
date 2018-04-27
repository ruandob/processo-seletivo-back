package com.processo.seletivo.domain.mantedora;

import com.processo.seletivo.core.exception.CustomDuplicatedException;
import com.processo.seletivo.core.exception.ExceptionMessageCode;
import com.processo.seletivo.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MantenedoraService extends AbstractService<Mantenedora> {

    @Autowired
    MantenedoraRepository mantenedoraRepository;

    @Override
    public Mantenedora save(Mantenedora mantenedora){
        if(mantenedora.getId() == 0){
            if(mantenedoraRepository.findByNomeOrCodigo(mantenedora.getNome(), mantenedora.getCodigo()).isEmpty()){
                return mantenedoraRepository.save(mantenedora);
            }
        }else if(mantenedoraRepository.findByIdAndNomeOrCodigo(mantenedora.getId(), mantenedora.getNome(), mantenedora.getCodigo()).isEmpty()){
            return mantenedoraRepository.save(mantenedora);
        }
        throw new CustomDuplicatedException(ExceptionMessageCode.MENSAGEM_REGISTRO_DUPLICADO);
    }
}
