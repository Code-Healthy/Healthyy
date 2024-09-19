package com.healthy.service.impl;

import com.healthy.dto.HabitCreateUpdateDTO;
import com.healthy.dto.HabitDetailsDTO;
import com.healthy.mapper.HabitMapper;
import com.healthy.model.entity.Habit;
import com.healthy.model.entity.HabitType;
import com.healthy.repository.HabitRepository;
import com.healthy.repository.HabitTypeRepository;
import com.healthy.service.AdminHabitService;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminHabitServiceImpl implements AdminHabitService {

    private final HabitRepository habitRepository;
    private final HabitTypeRepository habitTypeRepository;
    private final HabitMapper habitMapper;


    @Transactional(readOnly = true)
    @Override
    public List<HabitDetailsDTO> getAll() {
        List<Habit> habits = habitRepository.findAll();
        return habits.stream()
                .map(habitMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<HabitDetailsDTO> paginate(Pageable pageable) {
        return habitRepository.findAll(pageable)
                .map(habitMapper::toDetailsDTO);
    }

    @Override
    public HabitDetailsDTO findById(int id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        return habitMapper.toDetailsDTO(habit);
    }

    @Transactional
    @Override
    public HabitDetailsDTO create(HabitCreateUpdateDTO habitCreateUpdateDTO) {
        HabitType habitType = habitTypeRepository.findById(habitCreateUpdateDTO.getId())
                .orElseThrow(()-> new RuntimeException("Habit type not found with id: "+habitCreateUpdateDTO.getHabitTypeId()));

        Profile profile = profileMapper.toEntity(profileCreateUpdateDTO);
        profile.setUser(user);
        return profileMapper.toDetailsDTO(profileRepository.save(profile));
    }

    @Transactional
    @Override
    public HabitDetailsDTO update(Integer id, HabitCreateUpdateDTO habitCreateUpdateDTO) {
        Habit habitFromDb = habitRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Habit not found with id: "+id));

        HabitType habitType = habitTypeRepository.findById(habitCreateUpdateDTO.getHabitTypeId())
                .orElseThrow(()-> new RuntimeException("HabitType not found with id: "+habitCreateUpdateDTO.getHabitTypeId()));


        profileFromDb.setUser(user);
        profileFromDb.setAge(updatedProfile.getAge());
        profileFromDb.setGender(updatedProfile.getGender());
        profileFromDb.setHeight(updatedProfile.getHeight());
        profileFromDb.setWeight(updatedProfile.getWeight());
        profileFromDb.setHealthConditions(updatedProfile.getHealthConditions());

        return profileMapper.toDetailsDTO(profileRepository.save(profileFromDb));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habitRepository.delete(habit);
    }
    /*Para poder eliminar un ID de Hábito, primero tengo que elimnar desde pgAdmin, en la tabla Goals*/
    /* DELETE FROM goals where habit_id = 207 */
    /*207 = id de hábito como ejemplo*/
}