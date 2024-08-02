alter table t_team
alter column created_at set default now();

alter table t_team
    alter column modified_at set default now();