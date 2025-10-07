package com.techpro.upc.iam_service.domain.services;



import com.techpro.upc.iam_service.domain.model.aggregates.User;
import com.techpro.upc.iam_service.domain.model.queries.GetAllUsersQuery;
import com.techpro.upc.iam_service.domain.model.queries.GetUserByEmailQuery;
import com.techpro.upc.iam_service.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;


public interface UserQueryService {

    List<User> handle(GetAllUsersQuery query);


    Optional<User> handle(GetUserByIdQuery query);


    Optional<User> handle(GetUserByEmailQuery query);

}