package com.angelldca.store.mapper;

public interface IMapper <I, O>{
    public O map(I in);
}