package com.healthy.service.impl;

import com.healthy.dto.GoalCreateUpdateDTO;
import com.healthy.dto.GoalDetailsDTO;
import com.healthy.mapper.GoalMapper;
import com.healthy.model.entity.Goal;
import com.healthy.model.entity.Habit;
import com.healthy.model.entity.Plan;
import com.healthy.model.entity.User;
import com.healthy.repository.GoalRepository;
import com.healthy.repository.PlanRepository;
import com.healthy.repository.UserRepository;
import com.healthy.service.AdminGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.healthy.repository.HabitRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminGoalServiceImpl implements AdminGoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final PlanRepository planRepository;
    private final GoalMapper goalMapper;


    @Transactional(readOnly = true)
    @Override
    public List<GoalDetailsDTO> getAll() {
        List<Goal> goals = goalRepository.findAll();

        return goals.stream()
                .map(goalMapper::toDetailsDTO)
                .toList();
    }


    @Transactional(readOnly = true)
    @Override
    public Page<GoalDetailsDTO> paginate(Pageable pageable) {
        return goalRepository.findAll(pageable)
                .map(goalMapper::toDetailsDTO);
    }

    @Transactional
    @Override
    public GoalDetailsDTO create(GoalCreateUpdateDTO goalCreateUpdateDTO) {
        User user = userRepository.findById(goalCreateUpdateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Habit habit = habitRepository.findById(goalCreateUpdateDTO.getHabitId()).
                orElseThrow(() -> new RuntimeException("Habit not found"));
        Plan plan =planRepository.findById(goalCreateUpdateDTO.getPlanId()).
                orElseThrow(() -> new RuntimeException("Plan not found"));

        Goal goal = goalMapper.toEntity(goalCreateUpdateDTO);

        goal.setStartDate(LocalDateTime.now());
        goal.setUser(user);
        goal.setHabit(habit);
        goal.setPlan(plan);
        return goalMapper.toDetailsDTO(goalRepository.save(goal));
    }

    @Transactional
    @Override
    public GoalDetailsDTO update(Integer id, GoalCreateUpdateDTO updateGoal) {

        Goal goalFromDb = goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"));

        User user = userRepository.findById(updateGoal.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Habit habit = habitRepository.findById(updateGoal.getHabitId()).orElseThrow(() -> new RuntimeException("Habit not found"));
        Plan plan = planRepository.findById(updateGoal.getPlanId()).orElseThrow(() -> new RuntimeException("Plan not found"));

        //goal=goalMapper.toEntity(updateGoal);

        goalFromDb.setEndDate(updateGoal.getEndDate());
        goalFromDb.setGoalStatus(updateGoal.getGoalStatus());
        goalFromDb.setTargetValue(updateGoal.getTargetValue());
        goalFromDb.setCurrentValue(updateGoal.getCurrentValue());
        goalFromDb.setHabit(habit);
        goalFromDb.setPlan(plan);
        goalFromDb.setId(updateGoal.getId());
        goalFromDb.setUser(user);
        //goalFromDb.setTrackingRecords(updateGoal.getTrackingRecords());

        return goalMapper.toDetailsDTO(goalRepository.save(goalFromDb));
    }

    @Transactional(readOnly = true)
    @Override
    public GoalDetailsDTO findById(int id) {

        Goal goal = goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"));
        return goalMapper.toDetailsDTO(goal);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Goal goal = goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"));
        goalRepository.delete(goal);
    }
}