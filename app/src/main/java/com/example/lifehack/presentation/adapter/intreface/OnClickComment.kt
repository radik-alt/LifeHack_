package com.example.lifehack.presentation.adapter.intreface

import com.example.lifehack.data.entity.Comments.Data

interface OnClickComment {

    fun onClickComment(comment: Data, view: Int)
}