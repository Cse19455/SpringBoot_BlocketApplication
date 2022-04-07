package at.spengergasse.tests;

import _21224bhifPos1CsesiereiBlocketWiki.Application;
import _21224bhifPos1CsesiereiBlocketWiki.Domain.Block;
import _21224bhifPos1CsesiereiBlocketWiki.Services.BlockService;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.Dtos.BlockDto;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.TemporalValueFactory;
import _21224bhifPos1CsesiereiBlocketWiki.persistence.BlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class BlockServiceTest {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private TemporalValueFactory temporalValueFactory;
    private BlockService blockService;

    @BeforeEach
    void setup(){
        assertNotNull(blockRepository);
        blockService = new BlockService(blockRepository);
    }
/*
    @Test
    void ensureBlockServiceWorksProperlyWithMutateBlockCommand(){
        //given
        BlockDto block = Mockito.mock(BlockDto.class);
        //when
        Block addedReference = blockService.createInstanceByDTO(block);
        //assert
        assertEquals(block.blockDurability(), addedReference.getBlockDurability());
        assertEquals(block.size(), addedReference.getSize());
        assertEquals(block.name(), addedReference.getName());
    }
*/
    @Test
    void ensureBlockServiceFindsMutateBlockCommand(){
        BlockDto block = mockUpBlock(1,"HerbertBlock","Herbert",1);
        blockService.createInstanceByDTO(block);
        List<Block> addedReference = blockService.getBlock(block);
        assertNotNull(addedReference);
        assertEquals(1,addedReference.size());
    }

    @Test
    void ensureBlockServiceDeletesBlock(){
        BlockDto block = mockUpBlock(1,"HerbertBlock","Herbert",1);
        blockService.createInstanceByDTO(block);
        blockService.deleteBlock(block);
        List<Block> addedReference = blockService.getBlock(block);
        assertEquals(0,addedReference.size()); //Assert that the list iis empty
    }


    private BlockDto mockUpBlock(int durability,String blockname,String name,int size){
        return BlockDto.builder()
                .blockDurability(durability)
                .blockname(blockname)
                .name(name)
                .size(size)
                .created_at(temporalValueFactory.create_timestamp())
                .build();
    }
}
