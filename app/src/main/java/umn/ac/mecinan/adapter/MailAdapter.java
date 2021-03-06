package umn.ac.mecinan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.activity.MailDetails;
import umn.ac.mecinan.model.Mail;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder>{

    private Context mCtx;
    private List<Mail> mailList;

    public MailAdapter(Context mCtx, List<Mail> mailList){
        this.mCtx = mCtx;
        this.mailList = mailList;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mail_list, viewGroup, false);
        MailAdapter.MailViewHolder holder = new MailAdapter.MailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MailViewHolder mailViewHolder, int i) {
        final Mail mail = mailList.get(i);

        //mailViewHolder.iv_mailList_mailImage.setImageDrawable(mCtx.getResources().getDrawable(mail.getMailImage()));

        mailViewHolder.tv_mailList_title.setText(mail.getMailTitle());
        //mailViewHolder.tv_mailList_date.setText(String.valueOf(mail.getMailReceivedDate()));

        mailViewHolder.tv_mailList_projectName.setText(String.valueOf(mail.getProjectName()));
        mailViewHolder.tv_mailList_content.setText(mail.getMailContent());

        mailViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MailDetails.class);

                Bundle extras = new Bundle();
                extras.putString("mail_details_id_mail", mail.getIdMail());
                extras.putString("mail_details_category", mail.getMailCategory());
                extras.putString("mail_details_project_field", mail.getProjectField());
                extras.putString("mail_details_project_category", mail.getProjectCategory());
                extras.putString("mail_details_project_name", mail.getProjectName());
                extras.putString("mail_details_date", mail.getMailReceivedDate());
                extras.putString("mail_details_title", mail.getMailTitle());
                extras.putString("mail_details_content", mail.getMailContent());

                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_mailList_mailImage;
        TextView tv_mailList_title, tv_mailList_content, tv_mailList_date;
        TextView tv_mailList_projectName;
        View view;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_mailList_mailImage = itemView.findViewById(R.id.iv_mailList_mailImage);
            tv_mailList_title = itemView.findViewById(R.id.tv_mailList_title);
            tv_mailList_content = itemView.findViewById(R.id.tv_mailList_content);
            tv_mailList_date = itemView.findViewById(R.id.tv_mailList_date);
            tv_mailList_projectName = itemView.findViewById(R.id.tv_mailList_projectName);
            view = itemView;
        }
    }

    public void updateMailList(List<Mail> mails) {
        mailList.clear();

        mailList = mails;

        this.notifyDataSetChanged();
    }
}
