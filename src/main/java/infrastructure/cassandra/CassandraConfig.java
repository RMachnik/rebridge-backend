package infrastructure.cassandra;

import domain.PictureRepository;
import domain.ProjectRepository;
import domain.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "infrastructure.cassandra")
public class CassandraConfig extends AbstractCassandraConfiguration {

    public static final String KEYSPACE = "rebridge";
    @Value("${spring.data.cassandra.contact-points}")
    String contactPoint;

    @Override
    protected String getContactPoints() {
        return contactPoint;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE_DROP_UNUSED;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(KEYSPACE);

        return Arrays.asList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"domain"};
    }

    @Bean
    UserRepository cassandraUserRepository(UserCrudRepository userCrudRepository) {
        return new CassandraUserRepository(userCrudRepository);
    }

    @Bean
    ProjectRepository cassandraProjectRepository(ProjectCrudRepository projectCrudRepository) {
        return new CassandraProjectRepository(projectCrudRepository);
    }

    @Bean
    PictureRepository cassandraPictureRepository(PictureCrudRepository pictureCrudRepository) {
        return new CassandraPictureRepository(pictureCrudRepository);
    }
}
