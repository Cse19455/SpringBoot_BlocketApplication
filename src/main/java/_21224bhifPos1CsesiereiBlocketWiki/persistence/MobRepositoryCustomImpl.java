package _21224bhifPos1CsesiereiBlocketWiki.persistence;

import _21224bhifPos1CsesiereiBlocketWiki.Domain.Mob;
import _21224bhifPos1CsesiereiBlocketWiki.Domain.MobType;
import _21224bhifPos1CsesiereiBlocketWiki.Domain.UsableItem;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.Dtos.MobDto;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.MutateCommands.MutateMobCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor

@Component
public class MobRepositoryCustomImpl implements MobRepositoryCustom{

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Mob mob) {
        entityManager.persist(mob);
        entityManager.flush();
    }

    @Override
    public Mob findByName(String name) {
        Query query = entityManager.createQuery("SELECT id FROM Mob WHERE name = :name");
        query.setParameter("name", name);

        return entityManager.find(Mob.class, query.getSingleResult());
    }

    @Override
    public List<Mob> findByDrops(List<UsableItem> drops) {
        String sqlQueryString = "SELECT id FROM Mob WHERE drops = :drops";
        TypedQuery<Long> query = entityManager.createQuery(sqlQueryString, Long.class);
        query.setParameter("drops", drops);

        List<Long> ids = query.getResultList();
        return ids.stream().map(x-> entityManager.find(Mob.class,x)).collect(Collectors.toList());
    }

    @Override
    public Mob findByDTO(MobDto mutateMobCommand) {
        Query sqlQuery = entityManager.createQuery("SELECT id FROM Mob WHERE name = :name AND type = :type");
        sqlQuery.setParameter("type", mutateMobCommand.getType());
        sqlQuery.setParameter("name", mutateMobCommand.getName());

        return entityManager.find(Mob.class, sqlQuery.getSingleResult());
    }

    @Override
    public List<Mob> findByType(MobType type) {
        String sqlQueryString = "SELECT id FROM Mob WHERE type = :type";
        TypedQuery<Long> query = entityManager.createQuery(sqlQueryString, Long.class);
        query.setParameter("type", type);

        List<Long> ids = query.getResultList();
        return ids.stream().map(x-> entityManager.find(Mob.class,x)).collect(Collectors.toList());
    }
}