package at.spengergasse.tests.Service;

import _21224bhifPos1CsesiereiBlocketWiki.Application;
import _21224bhifPos1CsesiereiBlocketWiki.Domain.Block;
import _21224bhifPos1CsesiereiBlocketWiki.Services.BlockService;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.Dtos.BlockDto;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.TemporalValueFactory;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Interfaces.IBlockService;
import _21224bhifPos1CsesiereiBlocketWiki.persistence.BlockRepository;
import at.spengergasse.tests.MockUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void ensureBlockServiceWorksProperlyWithDTO(){
        //given
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        //when
        Block addedReference = blockService.createInstanceByDTO(block);
        //assert
        assertEquals(block.blockDurability(), addedReference.getBlockDurability());
        assertEquals(block.size(), addedReference.getSize());
        assertEquals(block.name(), addedReference.getName());
    }

    @Test
    void ensureBlockServiceFindsMutateBlockCommand(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        blockService.createInstanceByDTO(block);
        List<Block> addedReference = blockService.getBlock(block);
        assertNotNull(addedReference);
        assertEquals(1,addedReference.size());
    }

    @Test
    void ensureBlockServiceDeletesBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        blockService.createInstanceByDTO(block);
        blockService.deleteBlock(block);
        List<Block> addedReference = blockService.getBlock(block);
        assertEquals(0,addedReference.size()); //Assert that the list iis empty
    }

    @Test
    void ensureBlockServiceDeletesNonNoExistingBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        assertThrows(IllegalArgumentException.class,()->blockService.deleteBlock(block));
   }

    @Test
    void ensureBlockServiceGetsNoResultByDurability(){
        assertThrows(NoResultException.class,()->blockService.getBlocksByDurability(1));
    }

    @Test
    void ensureBlockServiceGetsBlocksByDurability(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        Block b =blockService.createInstanceByDTO(block);
        assertEquals(blockService.getBlocksByDurability(block.blockDurability()).get(0),b);
    }

    @Test
    void ensureBlockServiceInsertsBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        Block b =blockService.insertBlock(block);
        assertEquals(blockService.getAllBlocks().get(0),b);
    }

    @Test
    void ensureBlockServiceInsertsNoDuplicateBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        Block b =blockService.insertBlock(block);
        assertThrows(IllegalArgumentException.class,()->blockService.insertBlock(block));
    }

    @Test
    void ensureBlockServiceUpdatesBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        blockService.insertBlock(block);
        //Change Block parameters
        blockService.updateBlock(block);
        assertEquals(blockService.getAllBlocks().size(),1);
    }


    @Test
    void ensureBlockServiceUpdatesNoNonExistingBlock(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        assertThrows(IllegalArgumentException.class,()->blockService.updateBlock(block));
    }

    @Test
    void ensureBlockServiceDeletesAllBlocks(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","Herbert",1,temporalValueFactory.create_datetimestamp());
        blockService.insertBlock(block);
        blockService.insertBlock(MockUp.mockUpBlockDTO(10,"JAMESBlock","James",3,temporalValueFactory.create_datetimestamp()));
        assertEquals(blockService.getAllBlocks().size(),2);
        blockService.deleteAll();
        assertEquals(blockService.getAllBlocks().size(),0);
    }

    @Test
    void ensureBlockServiceChecksParameterInputName(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","",1,temporalValueFactory.create_datetimestamp());
        assertThrows(IllegalArgumentException.class,()->blockService.checkParameterInput(block));
    }

    @Test
    void ensureBlockServiceChecksParameterInputSize(){
        BlockDto block = MockUp.mockUpBlockDTO(1,"HerbertBlock","sdfsdf",0,temporalValueFactory.create_datetimestamp());
        assertThrows(IllegalArgumentException.class,()->blockService.checkParameterInput(block));
    }

}
