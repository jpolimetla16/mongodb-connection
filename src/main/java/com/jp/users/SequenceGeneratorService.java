package com.jp.users;

import java.math.BigInteger;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public  BigInteger getSequenceNumber(String sequenceName) {
		Query query = new Query(Criteria.where("id").is(sequenceName));
		Update update = new Update().inc("seq", 1);
		DBSequence dbSequence = mongoOperations
									.findAndModify(query, update,
											FindAndModifyOptions.options()
											.returnNew(true)
											.upsert(true), DBSequence.class);
		
		return Objects.isNull(dbSequence)?BigInteger.valueOf(1):dbSequence.getSeq();
		
	}
	

}
