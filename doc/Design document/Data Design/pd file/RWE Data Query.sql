SELECT 
obs_id, 
person_id, 
obs.concept_id conceptId, 
concept.NAME concept, 
f.NAME encounter, 
obs.obs_datetime obsDate,
obs.value_boolean,
obs.value_coded,
obs.value_datetime,
obs.value_drug,
obs.value_numeric,
obs.value_text
FROM obs 
INNER JOIN concept AS c ON c.concept_id = obs.concept_id
INNER JOIN encounter AS e ON obs.encounter_id = e.encounter_id
INNER JOIN form AS f ON f.form_id = e.form_id
INNER JOIN concept_name AS concept ON concept.concept_id = obs.concept_id 
AND concept.concept_id = c.concept_id AND concept.concept_name_type = 'FULLY_SPECIFIED'
INNER JOIN study_patient_map sm ON sm.patient_id = obs.person_id
INNER JOIN study s ON s.study_id = sm.study_id 
WHERE obs.person_id = 9390 AND s.study_id = 5

SELECT c.concept_id, cName.locale,cName.concept_name_type, cName.NAME FROM concept AS c INNER JOIN concept_name AS cName ON c.concept_id = cName.concept_id
WHERE cName.concept_name_type = 'FULLY_SPECIFIED';

SELECT * FROM concept_name AS cName WHERE cName.NAME = 'CHEST XRAY';

	