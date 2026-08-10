package com.nypunya.assignment.service;

import com.nypunya.assignment.dto.AssignmentRequest;
import com.nypunya.assignment.entity.Assignment;
import com.nypunya.assignment.entity.AssignmentStatus;
import com.nypunya.assignment.repository.AssignmentRepository;
import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service public class AssignmentService {
 private final AssignmentRepository repository; public AssignmentService(AssignmentRepository repository){this.repository=repository;}
 @Transactional public Assignment create(AssignmentRequest r,Long trainerId){Assignment a=new Assignment();a.setTitle(r.getTitle().trim());a.setDescription(r.getDescription());a.setCourseId(r.getCourseId());a.setTrainerId(trainerId);a.setDueAt(r.getDueAt());return repository.save(a);}
 @Transactional(readOnly=true) public Assignment get(Long id){return repository.findById(id).orElseThrow(()->new NoSuchElementException("Assignment not found"));}
 @Transactional(readOnly=true) public Page<Assignment> list(Long courseId,AssignmentStatus status,Pageable p){return status==null?repository.findByCourseId(courseId,p):repository.findByCourseIdAndStatus(courseId,status,p);}
 @Transactional public Assignment publish(Long id){Assignment a=get(id);if(a.getStatus()==AssignmentStatus.CLOSED)throw new IllegalStateException("Closed assignment cannot be published");a.setStatus(AssignmentStatus.PUBLISHED);return repository.save(a);}
 @Transactional public Assignment close(Long id){Assignment a=get(id);if(a.getStatus()==AssignmentStatus.CLOSED)return a;a.setStatus(AssignmentStatus.CLOSED);return repository.save(a);}
}
