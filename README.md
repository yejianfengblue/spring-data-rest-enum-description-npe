## Issue Description
In Spring Data REST 3.2.6.RELEASE, given an enum field annotated with org.springframework.data.rest.core.annotation.Description, 
in this example, gender
```java
@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    Long id;

    @Description("User name")
    String name;

    @Description("User gender, MALE or FEMALE")
    Gender gender;
}
```
when get JSON schema, then NullPointerException.  

## Reproduce Issue Steps
1. Run this Spring Boot application on localhost:8080
2. Visit http://localhost:8080/profile/users with header "Accept: application/schema+json", for example 
    ```shell script
    curl --request GET 'http://localhost:8080/profile/users' \
         --header 'Accept: application/schema+json' \
         --include
    ```
3. Get HTTP 500 response
4. Application log shows a NullPointerException thrown at PersistentEntityToJsonSchemaConverter.java:489
`org.springframework.data.rest.webmvc.json.PersistentEntityToJsonSchemaConverter.JacksonProperty.getSchemaProperty(BeanPropertyDefinition, TypeInformation<?>, ResourceDescription, InternalMessageResolver)`

## Preliminary Investigation
At PersistentEntityToJsonSchemaConverter.java:489
`description.getDefaultMessage().equals(resolvedDescription)`, the implementation class 
`AnnotationBasedResourceDescription` doesn't implement method `getDefaultMessage()` and its super implementation
`ResolvableResourceDescriptionSupport.getDefaultMessage()` return `null`.

## Workaround
Put the description to rest-messages.properties, see
https://docs.spring.io/spring-data/rest/docs/current/reference/html/#metadata.alps.descriptions

## Why use @Description which is not mentioned in Spring Data REST reference document?
I might forget to update the description if put to an external file rest-messages.properties. 
And [spring-data-rest-tests](https://github.com/spring-projects/spring-data-rest/blob/master/spring-data-rest-tests/spring-data-rest-tests-jpa/src/main/java/org/springframework/data/rest/webmvc/jpa/Person.java)
does demonstrates its usage. 
Actually I found this annotation `@Description` from `@RepositoryRestResource.itemResourceDescription`. 
Just try and the description is shown in ALPS document.